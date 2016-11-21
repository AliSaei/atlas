package uk.ac.ebi.atlas.commons.readers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlReader {

    private XMLConfiguration xmlConfiguration;

    public XmlReader(XMLConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

    public Multimap<String, String> read() {
        ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();

        Iterator<String> iterator =  xmlConfiguration.getKeys();
        while(iterator.hasNext()) {
            String key = iterator.next();
            List<String> values = xmlConfiguration.getList(String.class, key);
            builder.putAll(key, values);
        }

        return builder.build();
    }

    public String getString(String key) {
        return xmlConfiguration.getString(key);
    }

    public List<String> getList(String key) {
        List<String> list = xmlConfiguration.getList(String.class, key);
        return list == null ? new ArrayList<String>() : list;
    }

    public List<HierarchicalConfiguration<ImmutableNode>> configurationsAt(String key) {
        return xmlConfiguration.configurationsAt(key);
    }

    public String[] getStringArray(String query) {
        return xmlConfiguration.getStringArray(query);
    }

    public Configuration configurationAt(String key) {
        return xmlConfiguration.configurationAt(key);
    }

    public Document getDocument() {
        return xmlConfiguration.getDocument();
    }
}
