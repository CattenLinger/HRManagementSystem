package net.catten.hrsys.data;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
public class Pagination<T> {
    private int size; //Size of each page
    private int offset; //Current page
    private long total; //Count of all data
    private List<T> page; //Data in current page

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }
}
