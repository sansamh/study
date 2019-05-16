package file.locationparse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * DataModel 对应数据库model
 * </p>
 *
 * @author houcb
 * @since 2019-05-15 16:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {

    private Long id;
    private String code;
    private String name;
    private Long parentId;
    private String parentCode;
    private String parentName;


}
