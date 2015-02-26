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

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import ao.apbot.domain.Bot;

public class FakeSessionHandler extends IoHandlerAdapter {

    public FakeSessionHandler(Bot bot, AoChatBot aoChatBot) {
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
    }

    @Override
    public void sessionOpened(IoSession session) {
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    };

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
    }
}