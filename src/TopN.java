/**
 * Created by idejie on 16/12/26.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class TopN {
    int m_size;
    TreeSet<TopData> m_topSet = new TreeSet<TopData>();
    Map<String, Integer> m_topMap = new HashMap<String, Integer>();

    public TopN(int top){
        m_size = top;
    }

    //将需要排序的所有字符串依次增加到排序类中
    public void AddString(String key){
        if(m_topMap.containsKey(key)){
            m_topMap.put(key, m_topMap.get(key)+1);
        }else{
            m_topMap.put(key, 1);
        }
    }
    // 将map中的数据top统计到TreeSet中
    protected void MaptoSet(){
        Iterator<Entry<String, Integer>> iterator = m_topMap.entrySet().iterator();
        int temp;
        int minScore = 0xFFFFFFFF;
        while(iterator.hasNext()) {
            Entry<String, Integer> entry = iterator.next();
            temp = entry.getValue();
            if(minScore==0xFFFFFFFF){//第一次运行
                minScore = temp;
            }
            //首先填满m_topSet
            if(m_topSet.size()<m_size){
                m_topSet.add(new TopData(entry.getKey(), entry.getValue()));
                if(temp<minScore){
                    minScore = temp;//更新最低次数
                }
            }else if(temp>minScore){// m_topSet已经填满，并且该次数比最低次数高
                m_topSet.remove(m_topSet.last());//先删除m_topSet中的最低次数的实例
                m_topSet.add(new TopData(entry.getKey(), entry.getValue()));
                minScore = m_topSet.last().getScore();//更新最低次数
            }
        }
    }

    //获取排序结果，结果是递减的
    public Set<TopData> getResult(){
        MaptoSet();
        return m_topSet;
    }
}