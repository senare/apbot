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
package ao.misc;

/**
 * <p>
 * AOCharacter stores the ID, name, and level of a character.
 * </p>
 */
public class CharacterInfo {

    private final int m_id;
    private final String m_name;
    private final int m_level;
    private final int m_online;

    /**
     * Creates a new instance of AOCharacter
     *
     * @param id
     *            the id of this character
     * @param name
     *            the name of this character
     * @param level
     *            the level of this character
     * @param online
     *            ???
     */
    public CharacterInfo(int id, String name, int level, int online) {
        m_id = id;
        m_name = name;
        m_level = level;
        m_online = online;
    } // end AOCharacter()

    /** Returns the ID of this character. */
    public int getID() {
        return m_id;
    }

    /** Returns the name of this character. */
    public String getName() {
        return m_name;
    }

    /** Returns the level of this character. */
    public int getLevel() {
        return m_level;
    }

    public int getOnline() {
        return m_online;
    }

    public String toString() {
        return m_name;
    } // end toString()

    public String toString(boolean showID) {
        return m_name + (showID ? " (" + Integer.toHexString(m_id) + ")" : "");
    } // end toString()

} // end class AOCharacter
