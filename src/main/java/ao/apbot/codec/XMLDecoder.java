package ao.apbot.codec;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.codec.SAXHandler.EndParserException;

public class XMLDecoder extends CumulativeProtocolDecoder {

	/*
	 * As per XML specification 1.0, http://www.w3.org/TR/REC-xml
	 */
	private static final char XML_START_TAG = '<';
	private static final char XML_END_TAG = '>';
	private static final char XML_PI_TAG = '?';
	private static final char XML_COMMENT_TAG = '!';

	protected static enum ParseState {
		ELEMENT_START, ELEMENT_END, COMMENTS, ENDELEMENT, PI, UNDEFINED
	};

	protected static final int ELEMENT_START = 1;
	protected static final int ELEMENT_END = 2;

	private static Logger log = LoggerFactory.getLogger(XMLDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput decoderOutput) throws Exception {

		int startPosition = ioBuffer.position();

		if (!ioBuffer.hasRemaining()) {
			log.debug("NO bytes to read keep waiting...");
			return false;
		}

		// Continue to read the bytes and keep parsing
		char currentChar = '0', previousChar = '0';

		boolean rootElementStarted = false;
		boolean rootElementPresent = false;
		boolean isBalanced = false;

		int rootStartPosition, rootEndPosition;

		ParseState parsingState = ParseState.UNDEFINED;
		log.debug("Lets start decoding the XML");

		String root = null;

		boolean markedForEndElement = false;

		while (ioBuffer.hasRemaining()) {
			previousChar = currentChar;
			currentChar = (char) ioBuffer.get();

			switch (parsingState) {
			case ELEMENT_START:
				if (currentChar == XML_PI_TAG) {
					log.debug("Got PI Element");
					parsingState = ParseState.PI;
				} else if (currentChar == XML_COMMENT_TAG) {
					log.debug("Got Comment Element");
					parsingState = ParseState.COMMENTS;
				} else if ((currentChar == ' ' || currentChar == XML_END_TAG) && rootElementStarted && !rootElementPresent) {
					rootEndPosition = ioBuffer.position();
					rootElementPresent = true;

					// Copy the Root Element
					int cPos = ioBuffer.position();
					int mPos = ioBuffer.markValue();

					char[] rootChar = new char[cPos - mPos];
					for (int i = mPos - 1, j = 0; i < cPos - 1; i++) {
						rootChar[j++] = (char) ioBuffer.get(i);
					}

					root = new String(rootChar);
					log.debug("Root Element = " + root);
					parsingState = ParseState.ELEMENT_END;
					log.debug("Root Element detection completed " + rootEndPosition);
				} else if (currentChar == XML_END_TAG) {
					parsingState = ParseState.ELEMENT_END;
				} else if (!rootElementStarted && !rootElementPresent) {
					rootStartPosition = ioBuffer.position();
					ioBuffer.mark();
					rootElementStarted = true;
					log.debug("Got the root element at " + rootStartPosition);
				} else if (currentChar == '/') {
					// Change state
					if (previousChar == XML_START_TAG) {
						parsingState = ParseState.ENDELEMENT;
					}
				}
				break;

			case ENDELEMENT:
				if (currentChar == XML_END_TAG) {
					parsingState = ParseState.ELEMENT_END;

					int cPos = ioBuffer.position();
					int mPos = ioBuffer.markValue();

					char[] el = new char[cPos - mPos];
					for (int i = mPos - 1, j = 0; i < cPos - 1; i++) {
						el[j++] = (char) ioBuffer.get(i);
					}
					markedForEndElement = false;
					if (root.equalsIgnoreCase(new String(el))) {
						log.debug("XML is balanced." + root);
						isBalanced = true;
					}

					break;
				} else if (currentChar == ' ') {
					continue;
				} else {
					// mark the position, we need to compare the it to see that
					// if its the end element
					if (!markedForEndElement) {
						ioBuffer.mark();
						markedForEndElement = true;
					}
				}
				break;

			case ELEMENT_END:
				if (currentChar == XML_START_TAG) {
					parsingState = ParseState.ELEMENT_START;
				}
				break;

			case UNDEFINED:
				if (currentChar == XML_START_TAG) {
					parsingState = ParseState.ELEMENT_START;
				}
				break;

			case COMMENTS:
				if (currentChar == '-') {
					previousChar = currentChar;
				} else if (previousChar == '-' && currentChar == '>') {
					parsingState = ParseState.ELEMENT_END;
				}
				break;

			case PI:
				if (currentChar == '?') {
					previousChar = currentChar;
				} else if (previousChar == '?' && currentChar == XML_END_TAG) {
					parsingState = ParseState.ELEMENT_END;
				}
				break;

			default:
				break;
			}
		}

		if (isBalanced && !ioBuffer.hasRemaining()) {
			log.debug("No more bytes to process");
			ioBuffer.position(startPosition);
			decoderOutput.write(parserXML(ioBuffer));
			return true;
		}

		ioBuffer.position(startPosition);
		return false;
	}

	public Safepay parserXML(IoBuffer xmlBuffer) throws Exception {
		SAXParserFactory parserFactor = SAXParserFactory.newInstance();
		SAXParser parser = parserFactor.newSAXParser();
		SAXHandler handler = new SAXHandler();
		InputStream asInputStream = xmlBuffer.asInputStream();

		try {
			parser.parse(asInputStream, handler);
		} catch (EndParserException x) {
//			log.debug("We shouldn't do anything this is unfortunaly normal ...", x);
		}

		return handler.safepay;
	}
}