package filters.matrix_filters;

import filters.AbstractParametredFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMatrixFilter extends AbstractParametredFilter {
    protected int[][] matrix;
    protected int k;

    protected abstract void setMatrix();
}
