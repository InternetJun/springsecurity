//package com.example.handle;
//
//public class DefaultControllerHandler extends AbstractControllerHandler {
//    private static ILogger log = LoggerFactory.getLogger(DefaultControllerHandler.class);
//    private static int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
//
//    public DefaultControllerHandler(ProceedingJoinPoint proceedingJoinPoint) {
//        super(proceedingJoinPoint);
//    }
//
//    @Override
//    public Object handle() throws Throwable {
//        long timestamp = System.currentTimeMillis();
//        this.logIn();
//
//        ResponseDto responseDto;
//        boolean success = false;
//        try {
//            Object result = proceedingJoinPoint.proceed();
//            if (result instanceof ResponseDto) {
//                responseDto = (ResponseDto) result;
//            } else {
//                responseDto = ResponseDto.success(result);
//            }
//            success = true;
//            RuntimeHealthIndicator.successRequestCount++;
//        } catch (BusinessException e) {
////            RuntimeHealthIndicator.failedRequestCount++;
//            if (this.isDebugLogLevel()) {
//                log.error(e);
//            }
//
//            responseDto = new ResponseDto<>(e.getCode(), e.getMessage(), null);
//        } catch (Exception e) {
//            RuntimeHealthIndicator.failedRequestCount++;
//
//            if (this.isDebugLogLevel()) {
//                log.error(e);
//            }
//
//            responseDto = ResponseDto.failed(ExceptionDefinitions.ServerError, e.getMessage(), null);
//        } finally {
//            Calendar cale = Calendar.getInstance();
//            if (currentMonth != (cale.get(Calendar.MONTH) + 1)) {
//                String recodeKey = String.format("%d年%d月",
//                        cale.get(Calendar.YEAR), cale.get(Calendar.MONTH) + 1);
//                String recodeValue = "successCount:" + RuntimeHealthIndicator.successRequestCount +
//                        " failedCount:" + RuntimeHealthIndicator.failedRequestCount;
//                RuntimeHealthIndicator.historyRequestRecode.put(recodeKey, recodeValue);
//                RuntimeHealthIndicator.successRequestCount = 0;
//                RuntimeHealthIndicator.failedRequestCount = 0;
//                currentMonth = cale.get(Calendar.MONTH);
//            }
//        }
//
//        long duration = System.currentTimeMillis() - timestamp;
//        RuntimeHealthIndicator.markRestApiInvoked(this.methodName, (int) duration);
//        this.logOut(duration, success, JSON.toJSONString(responseDto));
//
//        return responseDto;
//    }
//
//    public boolean isDebugLogLevel() {
//        return log.isEnabled(LogLevel.DEBUG);
//    }
//}