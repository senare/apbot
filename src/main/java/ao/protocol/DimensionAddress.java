/*
 * DimensionAddress.java
 *
 * Created on March 18, 2008, 11:42 AM
 *************************************************************************
 * Copyright 2008 Paul Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ao.protocol;

/**
 * <p>AODimensionAddress is primarily a utility class that stores
 * the urls and ports of RK1, RK2, RK3 and Test-Live</p>
 * 
 * @author Paul Smith
 */
public enum DimensionAddress {
    RK1("Atlantean (Rubi-Ka 1)",         "chat.d1.funcom.com", 7101, 1),
    RK2("Rimor (Rubi-Ka 2)",             "chat.d2.funcom.com", 7102, 2),
    //RK3("Die Neue Welt (German Server)", "chat.d3.funcom.com",  7103),
    TEST("Test-Live (Test Server)",      "chat.dt.funcom.com", 7109, 0);
    
    private final String m_name;
    private final String m_url;
    private final int    m_port;
    private final int    m_id;
    
    /**
     * Creates a new instance of AODimensionAddress
     * 
     * 
     * @param name
     *        the name of the server
     * @param url
     *        the url of the server
     * @param port
     *        the port to connect to on the server
     */
    private DimensionAddress(String name, String url, int port, int id) {
        m_name = name;
        m_url  = url;
        m_port = port;
        m_id   = id;
    }   // end AODimensionAddress()
    
    /** Returns the name of the server */
    public String getName() { return m_name; }
    /** Returns the url of the server */
    public String getURL() { return m_url; }
    /** Returns the port to connect to on the server */
    public int getPort() { return m_port; }
    /** Returns the id for this dimension */
    public int getID() { return m_id; }
    
    @Override
    public String toString() { return m_name; }
    
}   // end enum AODimensionAddress
