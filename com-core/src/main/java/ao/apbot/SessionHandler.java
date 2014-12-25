package ao.apbot;

import java.util.Calendar;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.codec.Fact;
import ao.apbot.codec.MsgPacket;
import ao.apbot.codec.TimeFact;
import ao.apbot.domain.Bot;
import ao.apbot.pkg.auth.CharacterListPacket;
import ao.apbot.pkg.auth.LoginErrorPacket;
import ao.apbot.pkg.auth.LoginOkPacket;
import ao.apbot.pkg.auth.LoginRequestPacket;
import ao.apbot.pkg.auth.LoginSeedPacket;
import ao.apbot.pkg.auth.LoginSelectPacket;
import ao.apbot.pkg.auth.PingPacket;

public class SessionHandler extends IoHandlerAdapter {

	private final static Logger log = LoggerFactory.getLogger(SessionHandler.class);

	private String password;
	private String username;
	private String handle;

	private KieContainer kc;

	private AoChatBot aoChatBot;

	public SessionHandler(Bot bot, AoChatBot aoChatBot) {
		this.aoChatBot = aoChatBot;
		this.handle = bot.getName();
		this.username = bot.getUser();
		this.password = bot.getPassword();

		KieServices ks = KieServices.Factory.get();
		this.kc = ks.getKieClasspathContainer();

		Results results = this.kc.verify();
		for (Message msg : results.getMessages()) {
			log.info(msg.toString() + "  " + msg.getText());
		}
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		log.info("{} received {}", handle, message);

		if (message instanceof MsgPacket) {
			log.info("MsgPacket [{}]", ((MsgPacket) message).getMsg());
		}

		if (message instanceof Fact) {
			Fact pkg = (Fact) message;
			switch (pkg.getType()) {
			case PingPacket.TYPE:
				session.write(new PingPacket(((PingPacket) pkg).getMessage()));
				break;
			case LoginSeedPacket.TYPE:
				session.write(new LoginRequestPacket((LoginSeedPacket) pkg, username, password));
				break;
			case CharacterListPacket.TYPE:
				session.write(new LoginSelectPacket((CharacterListPacket) pkg, handle));
				break;
			case LoginOkPacket.TYPE:
				log.info("{} has logged on", handle);
				break;
			case LoginErrorPacket.TYPE:
				log.info("{} failed to logon", handle);
				break;
			default:

				if (pkg instanceof MsgPacket) {
					log.debug("Command " + ((MsgPacket) pkg).getCommand());
				}
				KieSession ksession = kc.newKieSession("APbotSession");

				// map for all bot's, or something ??
				ksession.setGlobal("session", session);
				ksession.setGlobal("manager", aoChatBot);

				ksession.insert(new TimeFact(Calendar.getInstance()));
				ksession.insert(pkg);
				ksession.fireAllRules();
				ksession.dispose();
			}
		} else {
			session.close(true);
		}
	}

	@Override
	public void sessionOpened(IoSession session) {
		log.info("{} connect", handle);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("{} sent {}", handle, message);
	};

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("{} idle {}", handle, status);
		session.write(new PingPacket("Java AOChat API ping"));
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Client caught exception ", cause);
		session.close(true);
	}
}