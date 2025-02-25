package graphql.language;


import com.google.common.collect.ImmutableList;
import graphql.Internal;
import graphql.PublicApi;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static graphql.Assert.assertNotNull;
import static graphql.collect.ImmutableKit.emptyList;
import static graphql.collect.ImmutableKit.emptyMap;
import static graphql.language.NodeChildrenContainer.newNodeChildrenContainer;
import static graphql.language.NodeUtil.assertNewChildrenAreEmpty;

@PublicApi
public class FloatValue extends AbstractNode<FloatValue> implements ScalarValue<FloatValue> {

    private final BigDecimal value;

    @Internal
    protected FloatValue(BigDecimal value, SourceLocation sourceLocation, List<Comment> comments, IgnoredChars ignoredChars, Map<String, String> additionalData) {
        super(sourceLocation, comments, ignoredChars, additionalData);
        this.value = value;
    }

    /**
     * alternative to using a Builder for convenience
     *
     * @param value of the Float
     */
    public FloatValue(BigDecimal value) {
        this(value, null, emptyList(), IgnoredChars.EMPTY, emptyMap());
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public List<Node> getChildren() {
        return emptyList();
    }

    @Override
    public NodeChildrenContainer getNamedChildren() {
        return newNodeChildrenContainer().build();
    }

    @Override
    public FloatValue withNewChildren(NodeChildrenContainer newChildren) {
        assertNewChildrenAreEmpty(newChildren);
        return this;
    }

    @Override
    public String toString() {
        return "FloatValue{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean isEqualTo(Node o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FloatValue that = (FloatValue) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public FloatValue deepCopy() {
        return new FloatValue(value, getSourceLocation(), getComments(), getIgnoredChars(), getAdditionalData());
    }

    public FloatValue transform(Consumer<Builder> builderConsumer) {
        Builder builder = new Builder(this);
        builderConsumer.accept(builder);
        return builder.build();
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        return visitor.visitFloatValue(this, context);
    }

    public static FloatValue of(double d) {
        return newFloatValue().value(d).build();
    }

    public static Builder newFloatValue() {
        return new Builder();
    }

    public static Builder newFloatValue(BigDecimal value) {
        return new Builder().value(value);
    }

    public static final class Builder implements NodeBuilder {
        private SourceLocation sourceLocation;
        private BigDecimal value;
        private ImmutableList<Comment> comments = emptyList();
        private IgnoredChars ignoredChars = IgnoredChars.EMPTY;
        private Map<String, String> additionalData = new LinkedHashMap<>();

        private Builder() {
        }

        private Builder(FloatValue existing) {
            this.sourceLocation = existing.getSourceLocation();
            this.comments = ImmutableList.copyOf(existing.getComments());
            this.value = existing.getValue();
            this.additionalData = new LinkedHashMap<>(existing.getAdditionalData());
        }


        public Builder sourceLocation(SourceLocation sourceLocation) {
            this.sourceLocation = sourceLocation;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder value(double value) {
            this.value = BigDecimal.valueOf(value);
            return this;
        }

        public Builder value(long value) {
            this.value = BigDecimal.valueOf(value);
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = ImmutableList.copyOf(comments);
            return this;
        }

        public Builder ignoredChars(IgnoredChars ignoredChars) {
            this.ignoredChars = ignoredChars;
            return this;
        }

        public Builder additionalData(Map<String, String> additionalData) {
            this.additionalData = assertNotNull(additionalData);
            return this;
        }

        public Builder additionalData(String key, String value) {
            this.additionalData.put(key, value);
            return this;
        }


        public FloatValue build() {
            return new FloatValue(value, sourceLocation, comments, ignoredChars, additionalData);
        }
    }
}
