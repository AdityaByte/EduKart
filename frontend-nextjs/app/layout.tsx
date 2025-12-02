"use client"

import Navbar from "@/components/navbar";
import "./globals.css";
import { SoftGradientBackground } from "@/components/ui/gradient-bg";
import FooterSection from "@/components/footer";
import { usePathname } from "next/navigation";

export default function AppLayout({children}: {children: React.ReactNode}) {

  const pathname = usePathname();

  const hideLayout = ["/login", "/signup"];
  const shouldHideLayout = hideLayout.includes(pathname);

  return (
    <html lang="en">
      <body cz-shortcut-listen="true" className="relative">
        {!shouldHideLayout && <SoftGradientBackground />}
        {!shouldHideLayout && <Navbar />}
        <main>{children}</main>
        {!shouldHideLayout && <FooterSection />}
      </body>
    </html>
  )
}