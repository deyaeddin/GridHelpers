package com.flowingcode.vaadin.addons.gridhelpers.it;

import com.flowingcode.vaadin.addons.gridhelpers.GridHelper;
import com.flowingcode.vaadin.addons.gridhelpers.Person;
import com.flowingcode.vaadin.addons.gridhelpers.TestData;
import com.flowingcode.vaadin.testbench.rpc.JsonArrayList;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;

@SuppressWarnings("serial")
@Route("it")
@ExtensionMethod(GridHelper.class)
public class IntegrationView extends Div implements IntegrationViewCallables {

  private Grid<Person> grid;

  @Getter
  private Integer toggledColumn;

  public IntegrationView() {
    setSizeFull();

    getElement().getStyle().set("flex-grow", "1");

    grid = new Grid<>();

    grid.addColumn(Person::getLastName).setHeader("Last name");
    grid.addColumn(Person::getFirstName).setHeader("First name");
    grid.addColumn(p -> p.isActive() ? "Yes" : "No").setHeader("Active");
    grid.addColumn(Person::getTitle).setHeader("Title");
    grid.addColumn(Person::getCountry).setHeader("Country");
    grid.addColumn(Person::getCity).setHeader("City");
    grid.addColumn(Person::getStreetAddress).setHeader("Street Address");
    grid.addColumn(Person::getPhoneNumber).setHeader("Phone Number");
    grid.addColumn(Person::getEmailAddress).setHeader("Email Address");
    grid.getColumns().forEach(c -> c.setAutoWidth(true));
    grid.getColumns().forEach(c -> c.setHidable(true));

    grid.setItems(TestData.initializeData());
    grid.addColumnToggleListener(ev -> {
      toggledColumn = grid.getColumns().indexOf(ev.getColumn());
    });

    add(grid);
  }

  @Override
  @ClientCallable
  public JsonValue $call(JsonObject invocation) {
    return IntegrationViewCallables.super.$call(invocation);
  }

  private List<Person> getItems() {
    return grid.getListDataView().getItems().collect(Collectors.toList());
  }

  @Override
  public void setColumnToggleVisible(boolean value) {
    grid.setColumnToggleVisible(value);
  }

  @Override
  public void setSelectOnClick(boolean value) {
    grid.setSelectOnClick(value);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    grid.setSelectionMode(selectionMode);
  }

  @Override
  public JsonArrayList<Integer> getSelectedRows() {
    List<Person> items = getItems();
    return JsonArrayList.fromIntegers(
        grid.getSelectedItems().stream().map(items::indexOf).sorted()
        .collect(Collectors.toList()));
  }

  @Override
  public void setHidable(int columnIndex, boolean hidable) {
    Column<?> column = grid.getColumns().get(columnIndex);
    column.setHidable(hidable);
  }

  @Override
  public void setSelectionFilterEnabled(boolean enabled) {
    if (enabled) {
      List<Person> items = getItems();
      grid.setSelectionFilter(item -> items.indexOf(item) % 2 == 0);
    } else {
      grid.setSelectionFilter(null);
    }
  }

  @Override
  public void setColumnHeader(int i, String header) {
    grid.getColumns().get(i).setHeader(header);
  }

  @Override
  public void setHidingToggleCaption(int i, String header) {
    Grid.Column<Person> col = grid.getColumns().get(i);
    GridHelper.setHidingToggleCaption(col, header);
  }

  @Override
  public void setEmptyGridLabel(String label) {
    Span span = new Span(label);
    span.setVisible(false);
    add(span);
    grid.setEmptyGridLabel(span);
  }

  @Override
  public void setSelectionColumnHidden(boolean b) {
    grid.setSelectionColumnHidden(b);
  }

  @Override
  public void setSelectionColumnFrozen(boolean b) {
    grid.setSelectionColumnFrozen(b);
  }

  @Override
  public void setArrowSelectionEnabled(boolean b) {
    grid.setArrowSelectionEnabled(b);
  }

  @Override
  public void addToolbarFooter(String footer) {
    grid.addToolbarFooter(new Span(footer));
  }

  @Override
  public void removeAllItems() {
    ((ListDataProvider<?>) grid.getDataProvider()).getItems().clear();
    grid.getDataProvider().refreshAll();
  }

  @Override
  public void setHeaderVisible(boolean visible) {
    grid.setHeaderVisible(visible);
  }

  @Override
  public void setFooterVisible(boolean visible) {
    grid.setFooterVisible(visible);
  }
}
