/*
 * Copyright 2026 albertzb [albertzb42@gmail.com].
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.albertzb.tarot.exception;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class TarotException extends RuntimeException {

    /**
     * Creates a new instance of <code>TarotException</code> without detail
     * message.
     */
    public TarotException() {
    }

    /**
     * Constructs an instance of <code>TarotException</code> with the specified
     * detail message.
     *
     * @param message the detail message.
     */
    public TarotException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>TarotException</code> with the specified
     * detail message and throwable cause.
     *
     * @param message the detail message.
     */
    public TarotException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance of <code>TarotException</code> with the specified
     * throwable cause.
     *
     * @param cause the nested cause of the exception. (A null value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public TarotException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of <code>TarotException</code> with the specified
     * detail message, cause and booleans.
     *
     * @param message the detail message.
     * @param cause the nested cause of the exception. (A null value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled or
     * disabled
     * @param writableStackTrace whether or not the stack trace should be
     * writable
     */
    public TarotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
