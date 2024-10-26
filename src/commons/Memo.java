/*
 *  备忘录类
 *  @author Wisteria
 **/
package commons;

import java.io.Serializable;
import java.util.Objects;

public class Memo implements Serializable {
    private static final long serialVersionUID = -57182530321885397L;
    private String title;
    private String content;

    public Memo() {
    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memo memo = (Memo) o;
        return Objects.equals(title, memo.title) && Objects.equals(content, memo.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    public String download() {
        return "(  )" + title + "-->" + content;
    }
}
