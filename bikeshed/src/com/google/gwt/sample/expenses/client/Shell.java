/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.expenses.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.expenses.shared.Report;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValueList;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.valuestore.shared.Property;
import com.google.gwt.valuestore.shared.Values;

import java.util.Date;
import java.util.List;

/**
 * UI shell for expenses sample app. A horrible clump of stuff that should be
 * refactored into proper MVP pieces.
 */
public class Shell extends Composite implements HasValueList<Values<Report>> {

  interface ShellUiBinder extends UiBinder<Widget, Shell> {
  }

  private static ShellUiBinder uiBinder = GWT.create(ShellUiBinder.class);
  @UiField
  Element error;
  @UiField
  TableElement table;
  @UiField
  TableRowElement header;
  @UiField
  ListBox users;

  public Shell() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void editValueList(boolean replace, int index,
      List<Values<Report>> newValues) {
    throw new UnsupportedOperationException();
  }

  public void setValueList(List<Values<Report>> newValues) {
    int r = 1; // skip header
    NodeList<TableRowElement> tableRows = table.getRows();
    for (int i = 0; i < newValues.size(); i++) {
      Values<Report> valueRow = newValues.get(i);

      if (r < tableRows.getLength()) {
        reuseRow(r, tableRows, valueRow);
      } else {
        TableRowElement tableRow = Document.get().createTRElement();

        TableCellElement tableCell = Document.get().createTDElement();
        tableCell.setInnerText(renderDate(valueRow, Report.CREATED));
        tableRow.appendChild(tableCell);

        tableCell = Document.get().createTDElement();
        /* status goes here */
        tableRow.appendChild(tableCell);

        tableCell = Document.get().createTDElement();
        tableCell.setInnerText(valueRow.get(Report.PURPOSE));
        tableRow.appendChild(tableCell);

        table.appendChild(tableRow);
      }
      r++;
    }
    while (r < tableRows.getLength()) {
      table.removeChild(tableRows.getItem(r));
    }
  }

  public void setValueListSize(int size, boolean exact) {
    throw new UnsupportedOperationException();
  }

  private <T> String renderDate(Values<T> values, Property<T, Date> property) {
    return DateTimeFormat.getShortDateTimeFormat().format(values.get(property));
  }

  /**
   * @param r
   * @param tableRows
   * @param valueRow
   */
  private void reuseRow(int r, NodeList<TableRowElement> tableRows,
      Values<Report> valueRow) {
    TableRowElement tableRow = tableRows.getItem(r);
    NodeList<TableCellElement> tableCells = tableRow.getCells();

    // tableCells.getItem(0).setInnerText(valueRow.get(Report.CREATED).toString());
    tableCells.getItem(2).setInnerText(valueRow.get(Report.PURPOSE));
  }
}
