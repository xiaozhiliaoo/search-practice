package org.apache.lucene.demo;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;

/**
 * @author lili
 * @date 2021/8/28 14:11
 */
public final class PartOfSpeechAttributeImpl extends AttributeImpl implements PartOfSpeechAttribute {

    private PartOfSpeech pos = PartOfSpeech.Unknown;

    @Override
    public void setPartOfSpeech(PartOfSpeech pos) {
        this.pos = pos;
    }

    @Override
    public PartOfSpeech getPartOfSpeech() {
        return pos;
    }

    @Override
    public void clear() {
        pos = PartOfSpeech.Unknown;
    }

    @Override
    public void copyTo(AttributeImpl target) {
        ((PartOfSpeechAttribute) target).setPartOfSpeech(pos);
    }

    @Override
    public void reflectWith(AttributeReflector reflector) {

    }
}
