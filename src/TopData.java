/**
 * Created by idejie on 16/12/26.
 */
public class TopData implements Comparable<TopData> {
    private String name;
    private int score;

    public TopData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(TopData o) {
        if(o.getScore()>this.score){
            return 1;
        }else{
            return -1;
        }
    }
}
