package stream.usage.stream_slicing;

import javax.xml.bind.annotation.XmlInlineBinaryData;

@slf4j
public class takeWhile {

    public class Dish {
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;

        public enum Type {
            MEAT, FISH, OTHER
        }


    }

}
