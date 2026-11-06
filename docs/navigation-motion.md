# Directional page navigation

The shared navigation host uses a 280 ms horizontal transition. An inner page
enters from the trailing edge while its parent moves back by one third of the
viewport. Back reverses the motion; RTL mirrors the direction. The viewport clips
page movement. Pages supply opaque backgrounds, and reduced motion disables the
transition. Account scope replacement still disposes old content immediately.

This ports the three generic files from
[HRIS Mobile 23ce87fa](https://github.com/fajarxfce/hris-mobile/commit/23ce87fa70b162a9aab5a5c1a1f6c4a00a5e2095).
Its release42 was inspected through four forward and four Back Android recordings,
including offline first/repeated room entries. Cached bubbles were present during
movement. No HRIS room cache, business policy or configuration is copied here.

The actual NavHost regression covers forward/Back movement and content in LTR/RTL,
reduced motion, route-owner restoration and account disposal. Independent Starter
root, Android and Web validation passed on unchanged inputs.
[Port evidence](measurements/navigation-motion-port-2026-11-06.json) records fresh
test counts, Android lint and exact source hashes. Apple CI on the exact port also
passes129 cases across34 suites; its artifact digest is verified. No Starter
application is installed on the phone.
