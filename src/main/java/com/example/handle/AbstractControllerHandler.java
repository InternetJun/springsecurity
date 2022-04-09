//package com.example.handle;
//
//public abstract class AbstractControllerHandler {
//    private static ILogger log = LoggerFactory.getLogger(AbstractControllerHandler.class);
//
//    private static Function<ProceedingJoinPoint, AbstractControllerHandler> build;
//
//    public static Function<ProceedingJoinPoint, AbstractControllerHandler> getBuild() {
//        return build;
//    }
//
//    public static void registerBuildFunction(Function<ProceedingJoinPoint, AbstractControllerHandler> build) {
//        Assert.isNotNull(build, "build");
//
//        AbstractControllerHandler.build = build;
//    }
//
//    protected ProceedingJoinPoint proceedingJoinPoint;
//    protected HttpServletRequest httpServletRequest;
//    protected String methodName;
//    protected String uri;
//    protected String requestBody;
//    protected String ip;
//    protected Method method;
//    protected boolean inDataMasking;
//    protected boolean outDataMasking;
//
//
//    public AbstractControllerHandler(ProceedingJoinPoint proceedingJoinPoint) {
//        Assert.isNotNull(proceedingJoinPoint, "proceedingJoinPoint");
//
//        this.proceedingJoinPoint = proceedingJoinPoint;
//        Signature signature = this.proceedingJoinPoint.getSignature();
//        this.httpServletRequest = this.getHttpServletRequest(this.proceedingJoinPoint.getArgs());
//        this.methodName = signature.getName();
//        this.uri = null == this.httpServletRequest ? null : this.httpServletRequest.getRequestURI();
//        this.requestBody = this.formatParameters(this.proceedingJoinPoint.getArgs());
//        this.ip = null == this.httpServletRequest ? "" : CommonHelper.getIp(this.httpServletRequest);
//        this.inDataMasking = false;
//        this.outDataMasking = false;
//        if (signature instanceof MethodSignature) {
//            MethodSignature methodSignature = (MethodSignature) signature;
//            try {
//                this.method = proceedingJoinPoint.getTarget().getClass().getMethod(this.methodName, methodSignature.getParameterTypes());
//                if (null != this.method) {
//                    LogDataMasking dataMasking = this.method.getDeclaredAnnotation(LogDataMasking.class);
//                    if (null != dataMasking) {
//                        this.inDataMasking = dataMasking.in();
//                        this.outDataMasking = dataMasking.out();
//                    }
//                }
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public abstract Object handle() throws Throwable;
//
//    protected void logIn() {
//        String requestBody = this.requestBody;
//        if (this.inDataMasking) {
//            requestBody = "Data Masking";
//        }
//        log.info(String.format("Start-[%s][%s][%s][body: %s]", this.ip, this.uri, this.methodName, requestBody));
//    }
//
//    protected void logOut(long elapsedMilliseconds, boolean success, String responseBody) {
//        if (success) {
//            if (this.outDataMasking) {
//                responseBody = "Data Masking";
//            }
//            log.info(
//                    String.format(
//                            "Success(%s)-[%s][%s][%s][response body: %s]",
//                            elapsedMilliseconds,
//                            this.ip,
//                            this.uri,
//                            this.methodName,
//                            responseBody));
//        } else {
//            log.warn(
//                    String.format(
//                            "Failed(%s)-[%s][%s][%s][request body: %s][response body: %s]",
//                            elapsedMilliseconds,
//                            this.ip,
//                            this.uri,
//                            this.methodName,
//                            this.requestBody,
//                            responseBody));
//        }
//    }
//
//    protected HttpServletRequest getHttpServletRequest(Object[] parameters) {
//        try {
//            if (null != parameters) {
//                for (Object parameter : parameters) {
//                    if (parameter instanceof HttpServletRequest) {
//                        return (HttpServletRequest) parameter;
//                    }
//                }
//            }
//
//            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        } catch (Exception e) {
//            log.error(e);
//
//            return null;
//        }
//    }
//
//    protected String formatParameters(Object[] parameters) {
//        if (null == parameters) {
//            return null;
//        } else {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < parameters.length; i++) {
//                Object parameter = parameters[i];
//                if (parameter instanceof HttpServletRequest || parameter instanceof HttpServletResponse) {
//                    continue;
//                }
//
//                stringBuilder.append(String.format("[%s]: %s.", i, JSON.toJSONString(parameter)));
//            }
//
//            return stringBuilder.toString();
//        }
//    }