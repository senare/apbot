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
package ao.apbot.codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import ao.apbot.pkg.ChannelMessagePacket;
import ao.apbot.pkg.PrivateChannelMessagePacket;
import ao.apbot.pkg.PrivateMessagePacket;

public abstract class MsgPacket extends Fact {

    protected List<String> allow = Lists.newArrayList("!");

    public MsgPacket(short type) {
        super(type);
    }

    public abstract int getCharacterId();

    public abstract String getMsg();

    public MsgPacket getReply(String msg) {
        if (this instanceof ChannelMessagePacket) {
            return new ChannelMessagePacket(((ChannelMessagePacket) this).getGroupId(), msg);
        } else if (this instanceof PrivateChannelMessagePacket) {
            return new PrivateChannelMessagePacket(((PrivateChannelMessagePacket) this).getGroupId(), msg);
        } else {
            return new PrivateMessagePacket(((PrivateMessagePacket) this).getCharacterId(), msg);
        }
    }

    protected String command = null;

    private int noParams = 0;

    private List<String> params = new ArrayList<>();

    protected void split() {
        params.addAll(Arrays.asList(getMsg().split("\\s+")));

        String pcommand = params.get(0);

        for (String prefix : allow) {
            if (pcommand.startsWith(prefix)) {
                this.command = pcommand = pcommand.substring(prefix.length());
            }
        }

        this.noParams = params.size();
        if (this.noParams > 1) {
            this.noParams--;
        }
    }

    public String getCommand() {
        if (params.size() < 1)
            this.split();

        return command;
    }

    public String getParam(int index) {
        if (params.size() < 1)
            this.split();

        if (params.isEmpty() || params.size() <= index) {
            return null;
        }
        return params.get(index);
    }

    public int getNoParams() {
        return this.noParams;
    }

    // private Pattern regExp =
    // Pattern.compile("(?:^!(?<command>\\S+)|)\\s+(?<params>\\S+)");
    //
    // private List<String> params = new ArrayList<>();
    //
    // private String command = "";
    //
    // public String getCommand() {
    // log.info("Command was called");
    // Matcher m = regExp.matcher(getMsg());
    // while (m.find()) {
    // if (m.group("command") != null) {
    // command = m.group("command");
    // }
    // if (m.group("params") != null)
    // params.add(m.group("params"));
    // }
    // log.info("Command was " + command + " and had " + params.size() +
    // " params");
    // return command;
    // }
    //
    // public String getParam(int index) {
    // return params.get(index - 1);
    // }
}