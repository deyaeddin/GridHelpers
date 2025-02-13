/*-
 * #%L
 * Grid Helpers Add-on
 * %%
 * Copyright (C) 2022 Flowing Code
 * %%
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
 * #L%
 */

package com.flowingcode.vaadin.addons.gridhelpers;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Freeze Selection Column")
@DemoSource
@Route(value = "grid-helpers/freeze-selection-column", layout = GridHelpersDemoView.class)
public class FreezeSelectionColumnDemo extends Div {

  public FreezeSelectionColumnDemo() {
    setSizeFull();

    Grid<Person> grid = new Grid<>();
    grid.setItems(TestData.initializeData());

    grid.setSelectionMode(SelectionMode.MULTI);

    grid.addColumn(Person::getFirstName).setHeader("First name");
    grid.addColumn(Person::getLastName).setHeader("Last name");
    grid.addColumn(Person::getTitle).setHeader("Title");
    grid.addColumn(Person::getCountry).setHeader("Country");
    grid.addColumn(Person::getCity).setHeader("City");
    grid.addColumn(Person::getStreetAddress).setHeader("Street Address");
    grid.addColumn(Person::getPhoneNumber).setHeader("Phone Number");
    grid.getColumns().forEach(c -> c.setAutoWidth(true));

    GridHelper.setSelectionColumnFrozen(grid, true);

    grid.setHeightFull();
    add(grid);
  }
}
