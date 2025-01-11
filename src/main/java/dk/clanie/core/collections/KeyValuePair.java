package dk.clanie.core.collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class KeyValuePair<K, V> {

	K k;
	V v;

    @JsonCreator
    public KeyValuePair(
        @JsonProperty("k") K k,
        @JsonProperty("v") V v
    ) {
        this.k = k;
        this.v = v;
    }


}
