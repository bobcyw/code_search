package com.caoyawen.code_search.infrastructure.file;

import java.io.IOException;
import java.util.function.Consumer;

public interface Loop {
    void forEach(Consumer<String> handleOneLine) throws IOException;
}
