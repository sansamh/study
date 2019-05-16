package file.locationparse;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * <p>
 * ReadModel excel读取的model
 * </p>
 *
 * @author houcb
 * @since 2019-05-15 16:07
 */
@Data
public class ReadModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String enName;
    @ExcelProperty(index = 1)
    private String adCode;
    @ExcelProperty(index = 2)
    private String cityCode;
}
