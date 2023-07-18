public class Normalization implements INormalize {
    INormalize iNormalize;
    public void setNormalizeMethod(INormalize iNormalize)
    {
        this.iNormalize = iNormalize;
    }
    public String normalize(String word)
    {
        return iNormalize.normalize(word);
    }
}
