package org.lili.nio.official.server;/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


import java.io.*;
import java.nio.*;
import java.nio.charset.*;

/**
 * A Content type that provides for transferring Strings.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 */
class StringContent implements Content {

    private static Charset ascii = Charset.forName("US-ASCII");

    private String type;                // MIME type
    private String content;

    StringContent(CharSequence c, String t) {
        content = c.toString();
        if (!content.endsWith("\n"))
            content += "\n";
        type = t + "; charset=iso-8859-1";
    }

    StringContent(CharSequence c) {
        this(c, "text/plain");
    }

    StringContent(Exception x) {
        StringWriter sw = new StringWriter();
        x.printStackTrace(new PrintWriter(sw));
        type = "text/plain; charset=iso-8859-1";
        content = sw.toString();
    }

    public String type() {
        return type;
    }

    private ByteBuffer bb = null;

    private void encode() {
        if (bb == null)
            bb = ascii.encode(CharBuffer.wrap(content));
    }

    public long length() {
        encode();
        return bb.remaining();
    }

    public void prepare() {
        encode();
        bb.rewind();
    }

    public boolean send(ChannelIO cio) throws IOException {
        if (bb == null)
            throw new IllegalStateException();
        cio.write(bb);

        return bb.hasRemaining();
    }

    public void release() throws IOException {
    }
}
