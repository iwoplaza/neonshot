package iwoplaza.meatengine.lang;

import java.util.HashMap;

public class LanguageData
{
    protected String languageKey;
    protected HashMap<String, String> entries;

    public String getLanguageKey()
    {
        return languageKey;
    }

    /**
     * Returns a localized version of the passed key.
     * Returns null if no localized version has been found.
     * @param unlocalizedKey
     * @return
     */
    public String getLocalized(String unlocalizedKey)
    {
        String localized = entries.get(unlocalizedKey);
        if (localized == null)
        {
            throw new UnlocalizedException(String.format("No '%s' localization found for '%s'", this.languageKey, unlocalizedKey));
        }
        return localized;
    }
}
