import Navbar from "@/components/navbar";
import "./globals.css";
import { SoftGradientBackground } from "@/components/ui/gradient-bg";
import FooterSection from "@/components/footer";

export default function AppLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body cz-shortcut-listen="true" className="relative">
        <SoftGradientBackground />
        <Navbar />
        <main>{children}</main>
        <FooterSection />
      </body>
    </html>
  )
}