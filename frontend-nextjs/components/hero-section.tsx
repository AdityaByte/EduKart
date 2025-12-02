'use client';

import { Swiper, SwiperSlide } from 'swiper/react'
import { Autoplay, EffectCoverflow } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/autoplay'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import 'swiper/css/effect-coverflow'
import Link from 'next/link'
import { ArrowRight, Menu, Rocket, X } from 'lucide-react'
import { Button } from '@/components/ui/button'
import { FiBook } from 'react-icons/fi'
import { MdSchool } from 'react-icons/md'
import { AiOutlineFileText } from 'react-icons/ai'
import { usePathname } from 'next/navigation';

export default function HeroSection() {
    const pathname = usePathname();

    return (
        <>
            <main className="overflow-hidden" id='home'>
                <section className="relative">
                    <div className="relative py-24 lg:py-28">
                        <div className="mx-auto max-w-7xl px-6 md:px-12">
                            <div className="text-center sm:mx-auto sm:w-10/12 lg:mr-auto lg:mt-0 lg:w-4/5">
                                <Link
                                    href="/"
                                    className="rounded-(--radius) mx-auto flex w-fit items-center gap-2 border p-1 pr-3">
                                    <span className="bg-muted rounded-[calc(var(--radius)-0.25rem)] px-2 py-1 text-xs">New</span>
                                    <span className="text-sm">Unlock Learning Potential</span>
                                    <span className="bg-(--color-border) block h-4 w-px"></span>

                                    <ArrowRight className="size-4" />
                                </Link>

                                <h1 className="mt-8 text-4xl font-semibold md:text-5xl xl:text-5xl xl:[line-height:1.125]">
                                    Simplify Study, <br /> Amplify Results <br />
                                </h1>
                                <p className="mx-auto mt-8 sm:mt-6 max-w-2xl text-wrap text-lg sm:block">Edukart brings you expertly crafted notes designed to take your learning to the next level!</p>

                                <div className="mt-8">
                                    <Button
                                        size="lg"
                                        asChild>
                                        <Link href="#">
                                            <Rocket className="relative size-4" />
                                            <span className="text-nowrap">Start Learning</span>
                                        </Link>
                                    </Button>
                                </div>
                            </div>
                            <div className="x-auto relative mx-auto mt-8 max-w-lg sm:mt-12">
                                <div className="absolute inset-0 -top-8 left-1/2 -z-20 h-56 w-full -translate-x-1/2 [background-image:linear-gradient(to_bottom,transparent_98%,theme(colors.gray.200/75%)_98%),linear-gradient(to_right,transparent_94%,_theme(colors.gray.200/75%)_94%)] [background-size:16px_35px] [mask:radial-gradient(black,transparent_95%)] dark:opacity-10"></div>
                                <div className="absolute inset-x-0 top-12 -z-[1] mx-auto h-1/3 w-2/3 rounded-full bg-blue-300 blur-3xl dark:bg-white/20"></div>

                                <Swiper
                                    slidesPerView={1}
                                    pagination={{ clickable: true }}
                                    loop
                                    autoplay={{ delay: 5000 }}
                                    modules={[Autoplay, EffectCoverflow]}>
                                    <SwiperSlide className="px-2">
                                        <div className="bg-background rounded-(--radius) h-44 max-w-lg border p-9">
                                            <div className="mx-auto h-fit w-full flex justify-center">
                                                <FiBook size={48} />
                                            </div>
                                            <p className="mt-6 text-center text-lg font-medium">Over 10,000 Notes Accessed Monthly</p>
                                        </div>
                                    </SwiperSlide>
                                    <SwiperSlide className="px-2">
                                        <div className="bg-background rounded-(--radius) h-44 max-w-lg border p-9">
                                            <div className="mx-auto h-fit w-full flex justify-center">
                                                <MdSchool size={48} />
                                            </div>
                                            <p className="mt-6 text-center text-lg font-medium">50% Improvement in Exam Scores</p>
                                        </div>
                                    </SwiperSlide>
                                    <SwiperSlide className="px-2">
                                        <div className="bg-background rounded-(--radius) h-44 max-w-lg border p-9">
                                            <div className="mx-auto h-fit w-full flex justify-center">
                                                <AiOutlineFileText size={48} />
                                            </div>
                                            <p className="mt-6 text-center text-lg font-medium">Study Time Reduced by 30%</p>
                                        </div>
                                    </SwiperSlide>
                                </Swiper>
                            </div>
                        </div>
                    </div>
                </section>
            </main>
        </>
    )
}