package muzak;

class KeyValueCombo implements Comparable<KeyValueCombo>
{
    private String m_key;
    private String m_value;

    KeyValueCombo(String key, String value)
    {
        m_key = key;
        m_value = value;
    }

    public String getKey()
    {
        return m_key;
    }

    public String getValue()
    {
        return m_value;
    }
    
    public String toString()
    {
        return m_value;
    }

    @Override
    public int compareTo(KeyValueCombo other)
    {
        return m_value.compareTo(other.m_value);
    }
}
