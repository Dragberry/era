package org.dragberry.era.business.reporting;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.dragberry.era.domain.ReportTemplate;

public class MSWordReportBuilder extends AbstractReportBuilder {

	@Override
	public void buildReport(ReportTemplate template, OutputStream os) {
		try (InputStream is = template.getTemplate().getBinaryStream()) {
			try (XWPFDocument doc = new XWPFDocument(is)) {
				processTables(doc.getTables(), dataProvider);
				processPragraphs(doc.getParagraphs(), dataProvider);
				doc.write(os);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private static void processPragraphs(List<XWPFParagraph> paragraphs, DataProvider dataProvider) {
		paragraphs.forEach(p -> {
			p.getRuns().forEach(run -> {
				run.getCTR().getTList().forEach(el -> {
					Matcher matcher = dataProvider.getPropertyPattern().matcher(el.getStringValue());
					if (matcher.find()) {
						el.setStringValue(matcher.replaceAll(dataProvider.getData(matcher.group(1))));
					}
				});
			});
		});
	}

	private static void processTables(List<XWPFTable> tables, DataProvider dataProvider) {
		if (!tables.isEmpty()) {
			tables.forEach(table -> {
				table.getRows().forEach(row -> {
					row.getTableCells().forEach(cell -> {
						processTables(cell.getTables(), dataProvider);
						processPragraphs(cell.getParagraphs(), dataProvider);
					});
				});
			});
		}
	}

}
