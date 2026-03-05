package lambda.entity;

public class Patient {

    private String firstNm;
    private String lastNm;
    private String mobNo;
    private String ssn;

    public Patient() {

    }

    public Patient(String firstNm, String lastNm, String mobNo, String ssn) {
        this.firstNm = firstNm;
        this.lastNm = lastNm;
        this.mobNo = mobNo;
        this.ssn = ssn;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstNm='" + firstNm + '\'' +
                ", lastNm='" + lastNm + '\'' +
                ", mobNo='" + mobNo + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }

    public String getFirstNm() {
        return firstNm;
    }

    public void setFirstNm(String firstNm) {
        this.firstNm = firstNm;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
