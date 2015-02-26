/*
    Copyright (C) 2015 Senare

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    contact : aperfectbot@gmail.com
    
 */
package ao.apbot;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.jboss.logging.Logger;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import ao.apbot.codec.Fact;
import ao.apbot.codec.MsgPacket;
import ao.apbot.domain.Bot;
import ao.apbot.pkg.auth.CharacterListPacket;
import ao.apbot.pkg.auth.LoginErrorPacket;
import ao.apbot.pkg.auth.LoginOkPacket;
import ao.apbot.pkg.auth.LoginRequestPacket;
import ao.apbot.pkg.auth.LoginSeedPacket;
import ao.apbot.pkg.auth.LoginSelectPacket;
import ao.apbot.pkg.auth.PingPacket;

public class SessionHandler extends IoHandlerAdapter {

    private final static Logger LOGGER = Logger.getLogger(SessionHandler.class);

    private String password;
    private String username;
    private String handle;

    private Template template;

    private KieContainer kc;

    private Map<String, Object> global = new HashMap<>();

    public SessionHandler(Bot bot, AoChatBot aoChatBot) {
        this.handle = bot.getName();
        this.username = bot.getUser();
        this.password = bot.getPassword();

        this.template = bot.getTemplate();
        if (template == Template.ADMIN) {
            global.put("manager", aoChatBot);
        }

        KieServices ks = KieServices.Factory.get();
        this.kc = ks.getKieClasspathContainer();

        Results results = this.kc.verify();
        for (Message msg : results.getMessages()) {
            LOGGER.info(msg.toString() + "  " + msg.getText());
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        try {
            LOGGER.infof("%s received %s", handle, message);

            if (message instanceof MsgPacket) {
                LOGGER.infof("MsgPacket [%s]", ((MsgPacket) message).getMsg());
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
                    LOGGER.infof("%s has logged on", handle);
                    break;
                case LoginErrorPacket.TYPE:
                    LOGGER.infof("%s failed to logon", handle);
                    break;
                default:

                    if (pkg instanceof MsgPacket) {
                        LOGGER.debug("Command " + ((MsgPacket) pkg).getCommand());
                        for (int i = 0; i <= ((MsgPacket) pkg).getNoParams(); i++) {
                            LOGGER.debugf("Param %s = %s ", i, ((MsgPacket) pkg).getParam(i));
                        }
                    }

                    KieSession ksession = kc.newKieSession(template.session);
                    ksession.setGlobal("session", session);

                    for (Entry<String, Object> entry : global.entrySet()) {
                        ksession.setGlobal(entry.getKey(), entry.getValue());
                    }

                    ksession.insert(pkg);
                    ksession.fireAllRules();
                    ksession.dispose();
                }
            } else {
                session.close(true);
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "%s received %s", handle, message);
        }
    }

    @Override
    public void sessionOpened(IoSession session) {
        LOGGER.infof("%s connect", handle);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOGGER.infof("%s dissconnect", handle);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        LOGGER.infof("%s sent %s", handle, message);
    };

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        LOGGER.infof("%s idle %s", handle, status);
        session.write(new PingPacket("Java AOChat API ping"));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        LOGGER.error("Client caught exception ", cause);
        session.close(true);
    }
}