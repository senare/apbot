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

public class ExtendedCharacterInfo {

    private final int m_id;
    private final String m_firstname;
    private final String m_nickname;
    private final String m_lastname;
    private final int m_level;
    private final int m_ailevel;
    private final String m_breed;
    private final String m_faction;
    private final String m_profession;
    private final int m_orgID;
    private final String m_orgName;
    private final String m_orgRank;
    private final int m_orgRankID;

    public ExtendedCharacterInfo(int id, String first, String name, String last, int level, int ailevel, String breed, String faction, String prof, int orgID, String orgName, String orgRank, int orgRankID) {
        m_id = id;
        m_firstname = first;
        m_nickname = name;
        m_lastname = last;
        m_level = level;
        m_ailevel = ailevel;
        m_breed = breed;
        m_faction = faction;
        m_profession = prof;
        m_orgID = orgID;
        m_orgName = orgName;
        m_orgRank = orgRank;
        m_orgRankID = orgRankID;
    } // end ExtendedCharacterInfo()

    /** Returns the ID of this character. */
    public int getID() {
        return m_id;
    }

    /** Returns the first name of this character. */
    public String getFirstName() {
        return m_firstname;
    }

    /** Returns the name of this character. */
    public String getName() {
        return m_nickname;
    }

    /** Returns the last name of this character. */
    public String getLastName() {
        return m_lastname;
    }

    /** Returns the level of this character. */
    public int getLevel() {
        return m_level;
    }

    /** Returns the alien level of this character. */
    public int getAILevel() {
        return m_ailevel;
    }

    public String getBreed() {
        return m_breed;
    }

    public String getFaction() {
        return m_faction;
    }

    public String getProfession() {
        return m_profession;
    }

    /** Returns the org ID of this character. */
    public int getOrgID() {
        return m_orgID;
    }

    /** Returns the org Name of this character. */
    public String getOrgName() {
        return m_orgName;
    }

    /** Returns the org Rank of this character. */
    public String getOrgRank() {
        return m_orgRank;
    }

    public int getOrgRankID() {
        return m_orgRankID;
    }

    @Override
    public String toString() {
        return m_nickname;
    } // end toString()

    public String toString(boolean showID) {
        return m_nickname + (showID ? " (" + Integer.toHexString(m_id) + ")" : "");
    } // end toString()

    public String display() {
        String display = (m_firstname.compareTo("") == 0 ? "" : m_firstname + " ");
        display += m_nickname;
        display += (m_firstname.compareTo("") == 0 ? "" : " " + m_lastname);
        display += " (" + m_level + "/" + m_ailevel + " " + m_faction + " " + m_breed + " " + m_profession;
        display += (m_orgName != null ? ", " + m_orgRank + " of " + m_orgName : ", Not in org") + ")";
        return display;
    }

} // end class AOCharacter
