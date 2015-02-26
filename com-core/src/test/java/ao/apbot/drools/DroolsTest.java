package ao.apbot.drools;

import static org.junit.Assert.assertTrue;

import org.apache.mina.core.session.DummySession;
import org.apache.mina.core.session.IoSession;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import ao.apbot.AoChatBot;
import ao.apbot.codec.MsgPacket;
import ao.apbot.pkg.PrivateChannelMessagePacket;

public class DroolsTest {

    private KieContainer kc;

    @Test
    public void testAdmin() {

        IoSession mock = PowerMockito.mock(IoSession.class);
        // ArgumentCaptor<MsgPacket> argument =
        // ArgumentCaptor.forClass(MsgPacket.class);
        // Mockito.verify(mock).write(argument.capture());

        KieServices ks = KieServices.Factory.get();
        this.kc = ks.getKieClasspathContainer();

        KieSession ksession = kc.newKieSession("adminSession");
        ksession.setGlobal("session", mock);
        ksession.setGlobal("manager", new AoChatBot());

        ksession.insert(new PrivateChannelMessagePacket(5, "help"));
        ksession.fireAllRules();
        ksession.dispose();

        // assertTrue(argument.getValue() instanceof
        // PrivateChannelMessagePacket);
    }

    @Test
    public void testOrg() {
        KieServices ks = KieServices.Factory.get();
        this.kc = ks.getKieClasspathContainer();

        KieSession ksession = kc.newKieSession("orgSession");
        ksession.setGlobal("session", new DummySession());

        ksession.insert(new PrivateChannelMessagePacket(5, "help"));
        ksession.fireAllRules();
        ksession.dispose();
    }
}