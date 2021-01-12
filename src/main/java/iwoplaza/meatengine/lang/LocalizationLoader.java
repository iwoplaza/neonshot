package iwoplaza.meatengine.lang;

import iwoplaza.meatengine.loader.ResourceLoader;

import java.io.IOException;
import java.util.List;

public class LocalizationLoader
{
    public static Localizer createFromResources() throws IOException
    {
        Localizer localizer = new Localizer();

        List<LanguageData> languageDataList = ResourceLoader.loadJSONResourcesFromDirectory(
                "/iwoplaza/neonshot/lang/",
                LanguageData.class);

        languageDataList.forEach(localizer::addLanguage);
        return localizer;
    }
}
