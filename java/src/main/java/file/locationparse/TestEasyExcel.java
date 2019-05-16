package file.locationparse;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.google.common.collect.Lists;
import util.FileUtil;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * TestEasyExcel
 * </p>
 *
 * @author houcb
 * @since 2019-05-15 15:11
 */
public class TestEasyExcel {


    private static String path = "/Users/box/study/java/src/main/java/file/locationparse/5ac9aa5989fc5856.xlsx";

    public static void main(String[] args) throws Exception {
        try {
            // 读取并解析excel文件
            InputStream inputStream = FileUtil.getResourcesFileInputStream(path);
            ExcelListener excelListener = new ExcelListener<ReadModel>();
            EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, ReadModel.class), excelListener);
            // 获取解析的model集合
            Map<String, ReadModel> map = excelListener.getMap();

            List<DataModel> dataModelList = Lists.newArrayListWithExpectedSize(map.size());

            Iterator<Map.Entry<String, ReadModel>> iterator = map.entrySet().iterator();
            // id为adCode s为id截取用
            String id, s;
            ReadModel readModel, parent;
            DataModel model;
            // 国家model
            ReadModel country = map.get("100000");
            while (iterator.hasNext()) {
                Map.Entry<String, ReadModel> next = iterator.next();
                id = next.getKey();
                readModel = next.getValue();
                System.out.println("处理：" + readModel);
                // 后四位为0 的是国家或者省级
                if (id.substring(2, 6).equalsIgnoreCase("0000")) {
                    // 国 没有父类
                    if (id.equalsIgnoreCase("100000")) {
                        model = buildWithoutParent(id, readModel);
                    } else {
                        // 省级
                        model = buildWithParent(id, readModel, country);
                    }

                } else {
                    // 地级市/区(最后两位为00) 父类为省级 截取 前两位 + 0000
                    if (id.substring(4, 6).equalsIgnoreCase("00")) {
                        s = id.substring(0, 2);
                        parent = map.get(s + "0000");
                        model = buildWithParent(id, readModel, parent);
                    } else {
                        // 县级市 截取前四位 + 00 = 地级市
                        s = id.substring(0, 4);
                        parent = map.get(s + "00");
                        if (Objects.isNull(parent)) {
                            // 省管县级市
                            s = id.substring(0, 2);
                            parent = map.get(s + "0000");
                        }
                        model = buildWithParent(id, readModel, parent);
                    }
                }

                dataModelList.add(model);

                System.out.println(dataModelList);
            }
            System.out.println("sql");
            // 组装sql
            handleSQL(dataModelList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleSQL(List<DataModel> dataModelList) {
        String s = "INSERT INTO `base`.`t_area`(`ID`, `CODE`, `NAME`, `PARENT_ID`, `PARENT_CODE`, `PARENT_NAME`, `DELETE_FLAG`, `CREATE_BY`, `CREATE_TIME`, `UPDATE_BY`, `UPDATE_TIME`) VALUES ";
        StringBuilder sql = new StringBuilder(s);
        StringBuilder builder;
        for (DataModel dataModel : dataModelList) {
            builder = new StringBuilder();
            builder.append("(");
            builder.append(dataModel.getId() + ",");
            builder.append("'" + dataModel.getCode() + "',");
            builder.append("'" + dataModel.getName() + "',");
            builder.append(dataModel.getParentId() + ",");
            builder.append("'" + dataModel.getParentCode() + "',");
            builder.append("'" + dataModel.getParentName() + "',");
            builder.append("1, NULL, NULL, 'admin', '2019-04-30 09:48:46'),");
            sql.append(builder.toString());
        }

        System.out.println(sql);
    }

    public static DataModel buildWithoutParent(String id, ReadModel readModel) {
        return DataModel.builder().id(Long.parseLong(id))
                .code(readModel.getCityCode())
                .name(readModel.getEnName()).build();
    }

    public static DataModel buildWithParent(String id, ReadModel readModel, ReadModel parent) {
        return DataModel.builder().id(Long.parseLong(id))
                .code(readModel.getCityCode())
                .name(readModel.getEnName())
                .parentId(Long.parseLong(parent.getAdCode()))
                .parentCode(parent.getCityCode())
                .parentName(parent.getEnName()).build();
    }
}
