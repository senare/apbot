package ao.apbot.codec;

import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLEncoder implements ProtocolEncoder {
	private static Logger log = LoggerFactory.getLogger(XMLEncoder.class);

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.setAutoExpand(true);
		encode(buffer.asOutputStream(), (Safepay) message);
		buffer.flip();
		out.write(buffer);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub
	}

	private void encode(OutputStream out, Safepay sp) throws Exception {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(out, "UTF-8");

		streamWriter.writeStartDocument("UTF-8", "1.0");
		streamWriter.writeStartElement("safepay");
		if (sp instanceof Request) {
			streamWriter.writeStartElement("do");
			streamWriter.writeAttribute("function", "posevent");

			node(streamWriter, "status", ((Request) sp).status);
			node(streamWriter, "info", ((Request) sp).info);
			node(streamWriter, "data", ((Request) sp).data);

			streamWriter.writeEndElement();
		}

		if (sp instanceof Response) {
			streamWriter.writeAttribute("location", ((Response) sp).location);
			streamWriter.writeAttribute("id", ((Response) sp).id);

			streamWriter.writeEmptyElement("result");
			streamWriter.writeAttribute("function", "posevent");
		}

		streamWriter.writeEndElement();
		streamWriter.writeEndDocument();

		out.close();
	}

	private void node(XMLStreamWriter streamWriter, String name, String value) throws XMLStreamException {
		log.debug("NODE name:{} value:{}", name, value);
		streamWriter.writeStartElement(name);
		streamWriter.writeCharacters(value);
		streamWriter.writeEndElement();
	}
}
