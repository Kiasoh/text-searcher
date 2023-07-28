public interface Normalizable {

    String normalize(String word);
}

class NullNormalization implements Normalizable{

    @Override
    public String normalize(String word) {
        return word;
    }
}