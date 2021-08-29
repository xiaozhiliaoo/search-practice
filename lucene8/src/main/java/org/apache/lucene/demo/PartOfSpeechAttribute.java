package org.apache.lucene.demo;

import org.apache.lucene.util.Attribute;

/**
 * @author lili
 * @date 2021/8/28 14:08
 */
public interface PartOfSpeechAttribute extends Attribute {
    public static enum PartOfSpeech {
        Noun, Verb, Adjective, Adverb, Pronoun, Preposition, Conjunction, Article, Unknown
    }

    public void setPartOfSpeech(PartOfSpeech pos);

    public PartOfSpeech getPartOfSpeech();

}
