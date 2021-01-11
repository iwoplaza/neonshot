package ivesiris.neonshot.engine.lang;

import ivesiris.neonshot.engine.loader.ResourceLoader;

import java.io.IOException;
import java.util.List;

public class LocalizationLoader
{
    public static Localizer createFromResources() throws IOException
    {
        Localizer localizer = new Localizer();

        List<LanguageData> languageDataList = ResourceLoader.loadJSONResourcesFromDirectory(
                "/lang/",
                LanguageData.class);

        languageDataList.forEach(localizer::addLanguage);
        return localizer;
    }
}
