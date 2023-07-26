public interface Normalizable {
    String normalize(String word);
}

class NullNormalization implements Normalizable{
    public NullNormalization(){
       super();
    }

    @Override
    public String normalize(String word) {
        return word;
    }
}