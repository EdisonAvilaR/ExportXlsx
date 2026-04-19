/**
 * 
 */
package export.reporte;

import java.io.OutputStream;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 */
public class ExportadorXLSX {
	
	public static void exportar(String nombreArchivo, List<String[]> datos, List<String> encabezados) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Datos");

        int fila = 0;

        // Crear encabezado
        Row rowHeader = hoja.createRow(fila++);
        for (int i = 0; i < encabezados.size(); i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(encabezados.get(i));
        }

        // Crear datos
        for (String[] filaDatos : datos) {
            Row row = hoja.createRow(fila++);
            for (int i = 0; i < filaDatos.length; i++) {
                row.createCell(i).setCellValue(filaDatos[i]);
            }
        }
        
        // Preparar la respuesta
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
        context.responseComplete();
    }
}
