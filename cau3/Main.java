package cau3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

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

class QuanLyHocPhi {
    private final List<SinhVien> danhSachHopLe = new ArrayList<>();
    private final List<String> danhSachLoi = new ArrayList<>();
    private final Set<String> maDaHopLe = new HashSet<>();

    public void themBanGhi(String line) {
        String trimmed = line == null ? "" : line.trim();
        String[] parts = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");
        String maSV = parts.length >= 2 ? parts[1] : "";

        try {
            if (parts.length != 3) {
                throw new DuLieuKhongHopLeException("Sai so luong truong du lieu");
            }

            String maHe = parts[0];
            String soTinChiStr = parts[2];

            if (!"CQ".equals(maHe) && !"CLC".equals(maHe) && !"LT".equals(maHe)) {
                throw new DuLieuKhongHopLeException("Ma he khong hop le");
            }

            int soTinChi;
            try {
                soTinChi = Integer.parseInt(soTinChiStr);
            } catch (NumberFormatException e) {
                throw new DuLieuKhongHopLeException("So tin chi khong phai so nguyen");
            }

            if (soTinChi < 1 || soTinChi > 30) {
                throw new DuLieuKhongHopLeException("So tin chi ngoai khoang [1, 30]");
            }

            if (maDaHopLe.contains(maSV)) {
                throw new DuLieuKhongHopLeException("Trung ma sinh vien hop le");
            }

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

            danhSachHopLe.add(sv);
            maDaHopLe.add(maSV);
        } catch (DuLieuKhongHopLeException ex) {
            danhSachLoi.add("INVALID " + maSV);
        }
    }

    public void sapXep() {
        danhSachHopLe.sort(
            Comparator.comparingLong(SinhVien::hocPhi).reversed()
                      .thenComparing(SinhVien::getMaSinhVien)
        );
    }

    public long tinhTongHocPhi() {
        long tong = 0;
        for (SinhVien sv : danhSachHopLe) {
            tong += sv.hocPhi();
        }
        return tong;
    }

    public void inKetQua() {
        for (String loi : danhSachLoi) {
            System.out.println(loi);
        }
        for (SinhVien sv : danhSachHopLe) {
            System.out.println(sv);
        }
        System.out.println("TONG " + tinhTongHocPhi());
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        while (firstLine != null && firstLine.trim().isEmpty()) {
            firstLine = br.readLine();
        }
        if (firstLine == null) {
            return;
        }

        int n = Integer.parseInt(firstLine.trim());
        QuanLyHocPhi ql = new QuanLyHocPhi();

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            ql.themBanGhi(line);
        }

        ql.sapXep();
        ql.inKetQua();
    }
}