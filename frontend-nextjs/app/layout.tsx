"use client"

import Navbar from "@/components/navbar";
import "./globals.css";
import { SoftGradientBackground } from "@/components/ui/gradient-bg";
import FooterSection from "@/components/footer";
import { usePathname } from "next/navigation";
import { AuthProvider } from "@/context/AuthContext";
import { Toaster } from "sonner";

export default function AppLayout({ children }: { children: React.ReactNode }) {

  const pathname = usePathname();

  const hideLayout = ["/login", "/signup"];
  const shouldHideLayout = hideLayout.includes(pathname);

  return (
    <html lang="en">
      <body cz-shortcut-listen="true" className="relative">
        <AuthProvider>
          {!shouldHideLayout && <SoftGradientBackground />}
          {!shouldHideLayout && <Navbar />}
          <main>{children}</main>
          {!shouldHideLayout && <FooterSection />}
          <Toaster
            position="top-right"
            expand={true}
            richColors
            closeButton
            toastOptions={{
              style: {
                background: "rgba(20, 20, 20, 0.7)",
                backdropFilter: "blur(12px)",
                borderRadius: "14px",
                border: "1px solid rgba(255, 255, 255, 0.15)",
                color: "white",
              },
              className: "shadow-xl",
            }}
          />
        </AuthProvider>
      </body>
    </html>
  )
}