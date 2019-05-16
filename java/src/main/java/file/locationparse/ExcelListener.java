package file.locationparse;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.Maps;
import file.locationparse.ReadModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExcelListener 监听器
 * </p>
 *
 * @author houcb
 * @since 2019-05-15 15:31
 */
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    private final List<T> data = new ArrayList<>();

    private final Map<String, ReadModel> map = Maps.newHashMap();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ReadModel model;
        for (T t : data) {
            model = (ReadModel) t;
            map.put(model.getAdCode(), model);
        }

        System.out.println("size" + map.size());

    }

    public List<T> getData() {
        return data;
    }

    public Map<String, ReadModel> getMap() {
        return map;
    }
}
