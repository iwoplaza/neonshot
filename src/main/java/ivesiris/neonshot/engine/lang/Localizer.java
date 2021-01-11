package ivesiris.neonshot.engine.lang;

import java.util.HashMap;
import java.util.Map;

public class Localizer implements ILocalizer
{

    private LanguageData current = null;
    private Map<String, LanguageData> langs;

    public Localizer()
    {
        this.langs = new HashMap<>();
    }

    public void addLanguage(LanguageData languageData)
    {
        this.langs.put(languageData.getLanguageKey(), languageData);
    }

    public void setLanguage(String languageKey)
    {
        LanguageData languageData = this.langs.get(languageKey);

        if (languageData == null)
        {
            throw new IllegalArgumentException(String.format("Tried to choose a language that doesn't exist: '%s'", languageKey));
        }

        this.current = languageData;
    }

    @Override
    public String getLocalized(String unlocalizedKey)
    {
        if (current == null)
            throw new IllegalStateException("Tried to localize with no language selected.");

        return current.getLocalized(unlocalizedKey);
    }

}
