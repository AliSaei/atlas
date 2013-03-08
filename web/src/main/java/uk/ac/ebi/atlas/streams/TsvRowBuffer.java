/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.streams;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

//ToDo: maybe it is possible to push up here more logic if I introduce a second generic argument for the type of objects that are in cyclic Iterator (FactorGroup / Contrast)
public abstract class TsvRowBuffer<T> {
    protected Queue<String> expressionLevelsBuffer = new LinkedList<>();

    public TsvRowBuffer reload(String... values) {
        checkState(this.expressionLevelsBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        expressionLevelsBuffer.clear();

        Collections.addAll(this.expressionLevelsBuffer, values);

        expressionLevelsBuffer.poll();

        return this;
    }

    public abstract T poll();
}
