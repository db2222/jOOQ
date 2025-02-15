/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import java.sql.ResultSet;

import org.jooq.BindingGetResultSetContext;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.Field;


/**
 * @author Lukas Eder
 */
class DefaultBindingGetResultSetContext<U> extends AbstractExecuteScope implements BindingGetResultSetContext<U> {

    private final ResultSet resultSet;
    private int             index;
    private Field<U>        field;
    private U               value;

    DefaultBindingGetResultSetContext(ExecuteContext ctx, ResultSet resultSet, int index) {
        super(ctx);

        this.resultSet = resultSet;
        this.index = index;
    }

    @Override
    public final ResultSet resultSet() {
        return resultSet;
    }

    @Override
    public final int index() {
        return index;
    }

    final void index(int i) {
        this.index = i;
    }

    @Override
    public final Field<U> field() {
        return field;
    }

    final void field(Field<U> f) {
        this.field = f;
    }

    @Override
    public void value(U v) {
        this.value = v;
    }

    final U value() {
        return value;
    }

    @Override
    public final <T> BindingGetResultSetContext<T> convert(final Converter<? super T, ? extends U> converter) {
        final DefaultBindingGetResultSetContext<U> outer = this;

        return new DefaultBindingGetResultSetContext<T>(ctx, resultSet, index) {
            @Override
            public void value(T v) {
                outer.value(converter.from(v));
            }
        };
    }

    @Override
    public String toString() {
        return "DefaultBindingGetResultSetContext [index=" + index + ", value=" + value + "]";
    }
}
