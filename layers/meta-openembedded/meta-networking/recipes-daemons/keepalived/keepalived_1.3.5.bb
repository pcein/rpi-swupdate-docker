SUMMARY = "High Availability monitor built upon LVS, VRRP and service pollers"
DESCRIPTION = "Keepalived is a routing software written in C. The main goal \
of this project is to provide simple and robust facilities for loadbalancing \
and high-availability to Linux system and Linux based infrastructures. \
Loadbalancing framework relies on well-known and widely used Linux Virtual \
Server (IPVS) kernel module providing Layer4 loadbalancing \
"
HOMEPAGE = "http://www.keepalived.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "http://www.keepalived.org/software/${BP}.tar.gz"

SRC_URI[md5sum] = "9964d295ec9d34ed3408b57d28847b68"
SRC_URI[sha256sum] = "c0114d86ea4c896557beb0d9367819a423ffba772bc5d7c548dc455e6b3bd048"

DEPENDS = "libnfnetlink openssl"

inherit autotools pkgconfig systemd update-rc.d

PACKAGECONFIG ??= "libnl snmp \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
"
PACKAGECONFIG[libnl] = "--enable-libnl,--disable-libnl,libnl"
PACKAGECONFIG[snmp] = "--enable-snmp,--disable-snmp,net-snmp"
PACKAGECONFIG[systemd] = "--with-init=systemd --with-systemdsystemunitdir=${systemd_system_unitdir},--with-init=SYSV,systemd"

EXTRA_OECONF = "--disable-libiptc"
EXTRA_OEMAKE = "initdir=${sysconfdir}/init.d"

do_install_append() {
    if [ -f ${D}${sysconfdir}/init.d/${BPN} ]; then
        chmod 0755 ${D}${sysconfdir}/init.d/${BPN}
        sed -i 's#rc.d/##' ${D}${sysconfdir}/init.d/${BPN}
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -D -m 0644 ${B}/${BPN}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    fi
}

FILES_${PN} += "${datadir}/snmp/mibs/KEEPALIVED-MIB.txt"

INITSCRIPT_NAME = "keepalived"
INITSCRIPT_PARAMS = "remove"

SYSTEMD_SERVICE_${PN} = "keepalived.service"
SYSTEMD_AUTO_ENABLE ?= "disable"
