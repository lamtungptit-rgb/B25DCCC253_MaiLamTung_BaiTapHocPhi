package cau4;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class MienGiamCS implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class MienGiamHN implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class MienGiamHB implements MienGiam {
    private static final long MUC_TOI_DA = 3_000_000L;

    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, MUC_TOI_DA);
    }
}

abstract class SinhVien {
    protected static final long DON_GIA = 450_000L;
    protected String maHe;
    protected String maSinhVien;
    protected int soTinChi;
    protected MienGiam mienGiam;

    public SinhVien(String maHe, String maSinhVien, int soTinChi) {
        this.maHe = maHe;
        this.maSinhVien = maSinhVien;
        this.soTinChi = soTinChi;
        this.mienGiam = null;
    }

    public abstract long hocPhi();

    public void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    public long soTienGiam() {
        if (mienGiam == null) {
            return 0L;
        }
        return mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    public String getMaHe() {
        return maHe;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    @Override
    public String toString() {
        return maSinhVien + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
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
    private static MienGiam taoChinhSach(String maChinhSach) {
        switch (maChinhSach) {
            case "CS":
                return new MienGiamCS();
            case "HN":
                return new MienGiamHN();
            case "HB":
                return new MienGiamHB();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            return;
        }

        int n = sc.nextInt();
        List<SinhVien> danhSach = new ArrayList<>();
        Map<String, SinhVien> mapSV = new LinkedHashMap<>();

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
                default:
                    sv = new SinhVienLT(maSV, soTinChi);
                    break;
            }
            danhSach.add(sv);
            mapSV.put(maSV, sv);
        }

        int m = sc.nextInt();
        List<String> notFoundList = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maChinhSach = sc.next();
            SinhVien sv = mapSV.get(maSV);
            if (sv == null) {
                notFoundList.add("NOT FOUND " + maSV);
            } else {
                sv.setMienGiam(taoChinhSach(maChinhSach));
            }
        }

        for (String nf : notFoundList) {
            System.out.println(nf);
        }

        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }

        String[] thuTuHe = {"CQ", "CLC", "LT"};
        for (String he : thuTuHe) {
            int dem = 0;
            long tongPhaiNop = 0;
            for (SinhVien sv : danhSach) {
                if (he.equals(sv.getMaHe())) {
                    dem++;
                    tongPhaiNop += sv.phaiNop();
                }
            }
            if (dem > 0) {
                System.out.println(he + " " + dem + " " + tongPhaiNop);
            }
        }
    }
}