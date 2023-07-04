package kz.attractor.java.LabWork;

import java.util.List;

public class DataModel {
    List<Member> members;

    public DataModel(List<Member> members) {
        this.members = members;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
