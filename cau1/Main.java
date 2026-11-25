package cau1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SinhVien {
    private String maSinhVien;
    private String hoTen;
    private String ngaySinh;

    public SinhVien(String maSinhVien, String hoTen, String ngaySinh) {
        this.maSinhVien = maSinhVien.trim();
        this.hoTen = chuanHoaTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    private String chuanHoaTen(String rawName) {
        String trimmed = rawName.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        String[] parts = trimmed.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String word = parts[i].toLowerCase();
            String formattedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(formattedWord);
        }
        return sb.toString();
    }

    private String chuanHoaNgaySinh(String rawDate) {
        String[] parts = rawDate.trim().split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    @Override
    public String toString() {
        return maSinhVien + " " + hoTen + " " + ngaySinh;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            return;
        }
        int n = Integer.parseInt(sc.nextLine().trim());
        List<SinhVien> danhSach = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String ma = sc.nextLine();
            String ten = sc.nextLine();
            String ns = sc.nextLine();
            danhSach.add(new SinhVien(ma, ten, ns));
        }

        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }
    }
}