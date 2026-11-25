import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class SinhVien {
    protected static final long DON_GIA = 450_000L;
    protected String maHe;
    protected String maSinhVien;
    protected int soTinChi;

    public SinhVien(String maHe, String maSinhVien, int soTinChi) {
        this.maHe = maHe;
        this.maSinhVien = maSinhVien;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    public String getMaHe() {
        return maHe;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    @Override
    public String toString() {
        return maSinhVien + " " + maHe + " " + hocPhi();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSinhVien, int soTinChi) {
        super("CQ", maSinhVien, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSinhVien, int soTinChi) {
        super("CLC", maSinhVien, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA * 3 / 2;
    }
}

class SinhVienLT extends SinhVien {
    private static final long PHI_QUAN_LY = 2_000_000L;

    public SinhVienLT(String maSinhVien, int soTinChi) {
        super("LT", maSinhVien, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + PHI_QUAN_LY;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            return;
        }
        int n = sc.nextInt();
        List<SinhVien> ds = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int soTinChi = sc.nextInt();

            SinhVien sv;
            switch (maHe) {
                case "CQ":
                    sv = new SinhVienCQ(maSV, soTinChi);
                    break;
                case "CLC":
                    sv = new SinhVienCLC(maSV, soTinChi);
                    break;
                case "LT":
                    sv = new SinhVienLT(maSV, soTinChi);
                    break;
                default:
                    continue;
            }
            ds.add(sv);
        }

        for (SinhVien sv : ds) {
            System.out.println(sv);
        }
    }
}